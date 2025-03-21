package com.study.stay.reposirory;

import com.study.stay.entity.StayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayInfoRepository extends JpaRepository<StayInfo, Long> {

}
