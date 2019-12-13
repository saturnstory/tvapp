package com.proton.tvapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proton.tvapp.model.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long>, ChannelRepositoryCustom{
		
}
