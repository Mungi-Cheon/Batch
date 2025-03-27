package com.study.stay.repository;

import com.study.stay.entity.StayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayInfoRepository extends JpaRepository<StayInfo, Long> {

}
