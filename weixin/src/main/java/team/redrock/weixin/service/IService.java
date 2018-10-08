package team.redrock.weixin.service;

import team.redrock.weixin.vo.Record;
import team.redrock.weixin.vo.User;

import java.util.List;

public interface IService {
    public boolean insertRecord(Record record);

    public List<Record> selectRecordList(String uid);

    public Boolean insertBind(User user);

    public User selectUser(String uid);

    public Boolean updateUser(User user);

}
