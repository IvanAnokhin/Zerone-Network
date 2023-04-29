package ru.skillbox.zerone.admin.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Builder.Default
  @Column(name = "time")
  private LocalDateTime time = LocalDateTime.now();

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private User author;

  @NotNull
  @NotBlank
  @Column(name = "title")
  private String title;

  @NotNull
  @NotBlank
  @Column(name = "post_text", columnDefinition = "text")
  private String postText;

  @NotNull
  @Builder.Default
  @Column(name = "update_date")
  @UpdateTimestamp
  private LocalDateTime updateTime = LocalDateTime.now();

  @NotNull
  @Builder.Default
  @Column(name = "is_blocked")
  private Boolean isBlocked = false;

  @NotNull
  @Builder.Default
  @Column(name = "is_deleted")
  private Boolean isDeleted = false;
}
