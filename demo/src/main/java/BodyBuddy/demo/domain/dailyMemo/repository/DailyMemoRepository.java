package BodyBuddy.demo.domain.dailyMemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BodyBuddy.demo.domain.dailyMemo.entity.DailyMemo;

@Repository
public interface DailyMemoRepository extends JpaRepository<DailyMemo, Long> {
}