package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.CenterSetting;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 리포지토리 인터페이스
 */
@Repository
public interface CenterSettingRepo extends JpaRepository<CenterSetting, Byte>
{
	
}
