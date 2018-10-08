package team.redrock.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.redrock.weixin.dao.Dao;
import team.redrock.weixin.service.IService;
import team.redrock.weixin.vo.Record;
import team.redrock.weixin.vo.User;

import java.util.List;

@Service
public class IServiceImp implements IService {
    @Autowired
    private Dao dao;
    @Override
    public boolean insertRecord(Record record) {
        return this.dao.insertRecords(record);
    }

    @Override
    public List<Record> selectRecordList(String uid) {
        return this.dao.getRecordList(uid);
    }

    @Override
    public Boolean insertBind(User user) {
        return this.dao.bindInsert(user);
    }

    @Override
    public User selectUser(String uid) {
        return this.dao.selectUser(uid);
    }

    @Override
    public Boolean updateUser(User user) {
        return this.dao.updateUser(user);
    }
}
