#!/bin/bash

# 1. 현재 켜져 있는 컨테이너를 도커에게 직접 물어봐서 확인합니다.
# 도커 프로세스(ps) 중에 backend-green이 켜져 있는지 확인
IS_GREEN_EXIST=$(sudo docker ps | grep backend-green)


if [ -z "$IS_GREEN_EXIST" ]; then
    # Green이 없으면 (Blue가 켜져있거나 처음 배포일 때) -> Target은 Green
    TARGET_COLOR="green"
    CURRENT_COLOR="blue"
else
    # Green이 켜져 있으면 -> Target은 Blue
    TARGET_COLOR="blue"
    CURRENT_COLOR="green"
fi

echo "🟢 현재 트래픽: $CURRENT_COLOR / 🚀 배포 대상: $TARGET_COLOR"

# 2. 타겟 컨테이너 및 Nginx 실행 (이전과 동일)
sudo docker-compose -f docker-compose.prod.yml pull backend-${TARGET_COLOR}
sudo docker-compose -f docker-compose.prod.yml up -d --remove-orphans nginx backend-${TARGET_COLOR}

# 3. 헬스 체크 (이전과 동일)
echo "⏳ $TARGET_COLOR 서버가 켜지기를 기다립니다..."
for i in {1..30}; do
    RESPONSE_CODE=$(sudo docker exec nginx curl -s -o /dev/null -w "%{http_code}" http://backend-${TARGET_COLOR}:8080/actuator/health || echo "000")

    if [ "$RESPONSE_CODE" -eq 200 ]; then
        echo "✅ 헬스 체크 성공! 트래픽을 전환합니다."
        break
    fi

    if [ $i -eq 30 ]; then
        echo "❌ 헬스 체크 실패. 배포를 중단합니다."
        sudo docker-compose -f docker-compose.prod.yml stop backend-${TARGET_COLOR}
        exit 1
    fi
    sleep 3
done

# 4. Nginx 트래픽 방향 전환 (이전과 동일)
echo "set \$service_url http://backend-${TARGET_COLOR}:8080;" > ./nginx/service-url.inc
sudo docker exec nginx nginx -s reload
echo "🔄 Nginx 트래픽 전환 완료!"

# 5. 기존 컨테이너 종료
# 처음 배포할 때는 끌 컨테이너가 없어서 에러가 날 수 있으므로 || true 를 붙여줍니다.
sudo docker-compose -f docker-compose.prod.yml stop backend-${CURRENT_COLOR} || true
echo "🎉 무중단 배포가 성공적으로 완료되었습니다!"