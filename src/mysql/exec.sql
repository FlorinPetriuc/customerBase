/*
 * Copyright (C) 2018 Florin Petriuc. All rights reserved.
 * Initial release: Florin Petriuc <petriuc.florin@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 */

CREATE DATABASE propellerhead;
CREATE TABLE customers(id INTEGER PRIMARY KEY AUTO_INCREMENT, status INTEGER, created DATETIME, name VARCHAR(128), details VARCHAR(128));
CREATE TABLE notes(id INTEGER PRIMARY KEY AUTO_INCREMENT, note VARCHAR(256), modified DATETIME, customerID INTEGER, FOREIGN KEY(customerID) REFERENCES customers(id));