/*
 * File        : chttp.h
 * Author      : Vincent Cheung
 * Date        : Jan. 21, 2015
 * Description : This is header file of chttp.c.
 *
 * Copyright (C) Vincent Chueng<coolingfall@gmail.com>
 *
 */

#ifndef __CHTTP_H__
#define __CHTTP_H__

void chttp_get(char *url);
void chttp_post(char *url, char *app_version, char *channel, char *device, char *device_id, char *event,
    char *event_type, char *ip, char *network, char *os, char *os_version, char *screen, char *company, char *time);

#endif