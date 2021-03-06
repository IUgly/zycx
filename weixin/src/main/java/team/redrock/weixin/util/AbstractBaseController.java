package team.redrock.weixin.util;

import org.springframework.context.MessageSource;

import javax.annotation.Resource;
import java.util.Locale;

public abstract class AbstractBaseController {
	@Resource
	private MessageSource messageSource; // 自动注入此资源对象
	public String getMessage(String key, String... args) {
		return this.messageSource.getMessage(key, args, Locale.getDefault()); 
	}
}
