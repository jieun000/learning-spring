package com.myintellij.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) // Auditing을 적용하기 위해 @EntityListeners 추가
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용하는 어노테이션으로 부모 클래스를 상속받는 자식 클래스에 매핑 정보만 제공
@Getter @Setter
public class BaseTimeEntity {
    @CreatedDate // 엔티티가 생성되어 저장 시 시간 자동 저장
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate // 엔티티의 값을 변경 시 시간 자동 저장
    private LocalDateTime updateTime;
}
