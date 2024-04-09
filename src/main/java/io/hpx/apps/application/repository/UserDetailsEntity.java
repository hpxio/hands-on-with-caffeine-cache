package io.hpx.apps.application.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_details")
public class UserDetailsEntity {

  private boolean active;

  @Id
  @GeneratedValue
  private int uid;
  private int age;

  @Column(unique = true)
  private String uuid;
  private String userName;
  private String userNickName;
}
