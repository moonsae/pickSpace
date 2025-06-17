package com.space.backend.resolver;

import com.space.backend.common.annotation.UserSession;
import com.space.backend.domain.user.service.UserService;
import com.space.backend.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {
    private final UserService userService;
    @Override
    public boolean supportsParameter(MethodParameter parameter){
        var annotation = parameter.hasParameterAnnotation(UserSession.class);
        var parameterType = parameter.getParameterType().equals(UserEntity.class);
        return (annotation && parameterType);
    }
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        var requestContext = RequestContextHolder.getRequestAttributes();
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        var userEntity = userService.getUserWithThrow(Long.parseLong(userId.toString()));

        return UserEntity.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .lastLoginAt(userEntity.getLastLoginAt())
                .registeredAt(userEntity.getRegisteredAt())
                .build();
    }
}
