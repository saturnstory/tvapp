package com.proton.tvapp.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proton.tvapp.repository.ChannelRepository;
import com.proton.tvapp.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelRepository channelReposiyory;
	

}
