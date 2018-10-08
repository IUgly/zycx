package team.redrock.weixin.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import team.redrock.weixin.vo.Record;
import team.redrock.weixin.vo.User;

import java.util.List;

@Mapper
@Component
public interface Dao {
    @Select("SELECT * FROM record WHERE uid = #{uid}")
    List<Record> getRecordList(String uid);

    @Insert("INSERT into record(hours,content,start_time,title,addway,status,uid) value (#{hours},#{content},#{start_time},#{title},#{addWay},#{status},#{uid})")
    Boolean insertRecords(Record record);

    @Insert("Insert into id_account(uid, account, password) value (#{uid},#{account},#{password})")
    Boolean bindInsert(User user);

    @Select("Select * from id_account where uid = #{uid}")
    User selectUser(String uid);

    @Update("update id_account set account=#{account},password=#{password} where uid = #{uid}")
    Boolean updateUser(User user);
}
