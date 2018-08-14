package com.dzeru.combalph.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.mustache.jmustache.LocalizationMessageInterceptor;

import java.util.Locale;

/**
   Copyright 2018, Dzeru, Combalph

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

@Configuration
public class LocalizationConfig implements WebMvcConfigurer
{
	@Autowired
	MessageSource messageSource;

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(localizationMessageInterceptor());
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor()
	{
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Bean
	public LocaleResolver localeResolver()
	{
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("en"));
		return localeResolver;
	}

	@Bean
	public LocalizationMessageInterceptor localizationMessageInterceptor()
	{
		LocalizationMessageInterceptor lmi = new LocalizationMessageInterceptor();
		lmi.setLocaleResolver(localeResolver());
		lmi.setMessageSource(messageSource);
		return lmi;
	}
}
