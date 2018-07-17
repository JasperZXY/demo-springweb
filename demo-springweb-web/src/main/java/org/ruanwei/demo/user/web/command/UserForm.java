package org.ruanwei.demo.user.web.command;

import java.util.Arrays;

import org.ruanwei.demo.user.dao.entity.UserEntity;
import org.ruanwei.demo.user.dao.entity.UserEntity;
import org.ruanwei.demo.user.service.dto.UserDto;


@SuppressWarnings("serial")
public class UserForm extends UserDto {
	public UserForm() {
	}


	public UserForm(String name, int age) {
		super(name, age);
	}

	private String[] hobbyArray;

	public String[] getHobbyArray() {
		return hobbyArray;
	}

	public void setHobbyArray(String[] hobbyArray) {
		this.hobbyArray = hobbyArray;
		int i = 0;
		if (hobbyArray != null && hobbyArray.length > 0) {
			for (String hobby : hobbyArray) {
				int iHobby = Integer.parseInt(hobby);
				i = i | iHobby;
			}
		}
		super.setHobby(i);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserForm [hobbyArray=")
				.append(Arrays.toString(hobbyArray)).append("]");
		builder.append(super.toString());
		return builder.toString();
	}
}
