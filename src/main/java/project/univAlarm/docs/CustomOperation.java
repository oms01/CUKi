package project.univAlarm.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        security = @SecurityRequirement(name="JWT"),
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "성공"
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "4XX",
                        description = "실패",
                        content = @Content()
                )
        }
)
public @interface CustomOperation {
    String summary();
}
