package dev.Nithin.OAuthUserSignIn.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @CreatedDate
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
