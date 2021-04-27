package com.raudonikis.core.providers.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class IODispatcher