package com.ucmed.model.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by MoLei on 2018/6/28.
 */
@Component
public class ProjectConfig {
	
	@Value("${project.version}")
    private String projectVersion;

	public String getProjectVersion() {
		return projectVersion;
	}
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
}
