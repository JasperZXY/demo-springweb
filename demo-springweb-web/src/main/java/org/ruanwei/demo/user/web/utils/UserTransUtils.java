package org.ruanwei.demo.user.web.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.ruanwei.demo.user.dao.entity.UserEntity;
import org.ruanwei.demo.user.web.command.UserForm;
import org.ruanwei.util.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by zhongxianyao.
 * Time 2018/7/17
 * Desc 文件描述
 */
public class UserTransUtils {
    public static UserForm trans2UserForm(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return BeanUtils.copy(userEntity, UserForm.class);
    }

    public static List<UserForm> trans2UserFormList(List<UserEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(UserTransUtils::trans2UserForm).collect(Collectors.toList());
    }

}