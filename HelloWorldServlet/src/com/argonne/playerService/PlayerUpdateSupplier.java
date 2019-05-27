package com.argonne.playerService;

import com.argonne.courseServices.CourseUpdateConsumer;

public interface PlayerUpdateSupplier {
	
	public void addConsumer(PlayerUpdateConsumer consumer);

	public void removeConsumer(PlayerUpdateConsumer consumer);

}
