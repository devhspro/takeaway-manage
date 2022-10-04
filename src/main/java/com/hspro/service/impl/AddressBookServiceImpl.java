package com.hspro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hspro.enity.AddressBook;
import com.hspro.mapper.AddressBookMapper;
import com.hspro.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
