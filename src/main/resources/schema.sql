-- notifications 테이블 생성 후 FULLTEXT 인덱스 추가
-- Hibernate가 테이블을 생성한 뒤에 실행되도록 설정되어 있습니다.
ALTER TABLE notifications ADD FULLTEXT INDEX idx_fulltext_title (title);
