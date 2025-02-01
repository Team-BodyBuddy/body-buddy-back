package BodyBuddy.demo.domain.inbody.dto;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.inbody.entity.InBody;
import BodyBuddy.demo.global.common.commonEnum.InBodyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AvatarInBodyDTO{
  private Long memberId;
  private Float currentWeight;
  private Float currentMuscle;
  private Float currentBodyFat;
  private Float previousWeight;
  private Float previousMuscle;
  private Float previousBodyFat;

  private final InBodyStatus weightStatus;
  private final InBodyStatus muscleStatus;
  private final InBodyStatus bodyFatStatus;
}