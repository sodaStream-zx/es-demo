package zxx.pri.core.mapper;

import org.apache.ibatis.annotations.*;
import zxx.pri.core.entity.DataInfo;
import zxx.pri.core.entity.SearchDto;

import java.util.List;
import java.util.Map;

/**
 * 数据详情 数据持久层
 *
 * @Author TangChao
 * @Date 15:22 2019/1/18 0018
 * @Param
 * @return
 **/

@Mapper
public interface DataInfoMapper {

    @Select(value = "SELECT real_id FROM t_user_task ")
    List<Long> listOfReaIds();
    /**
     * 模糊查询和分页-- 统计总数
     *
     * @return java.lang.Long
     * @Author TangChao
     * @Date 9:10 2019/1/19 0019
     * @Param [imie, content, start, end]
     **/
    @Select("<script>" +
            "select count(d.id)  from t_data_info  as d " +
            " INNER JOIN  t_task_data as t ON d.id = t.id " +
            " INNER JOIN  t_user_task as u ON t.search_id = u.search_id " +
            "<where>" +
            " u.imie = #{phone} " +
            "<if test='content !=null '>" +
            "and d.content LIKE CONCAT('%',#{content},'%') " +
            "</if>" +
            "<if test='start !=null '>" +
            "and d.publish_date between  #{start} and  #{end} " +
            "</if>" +
            "</where>" +
            "</script>"
    )
    Long searchDataCount(@Param(value = "phone") String phone, @Param(value = "content") String content,
                         @Param(value = "start") String start, @Param(value = "end") String end);

    /**
     * 模糊查询和分页-- 数据列表
     *
     * @return java.util.List<com.yunce.cloud.yuncecloudsearch.entity.DataInfo>
     * @Author TangChao
     * @Date 9:11 2019/1/19 0019
     * @Param [imie, content, start, end, pageBegainNum, pageSize]
     **/
    @Select("<script>" +
            " select d.id,d.read,d.title,d.content,d.publish_date,d.type_label,d.warn_level,d.source_name,d.risk_value,d.url " +
            " from t_data_info as d " +
            " INNER JOIN  t_task_data as t ON d.id = t.id " +
            " INNER JOIN  t_user_task as u ON t.search_id = u.search_id " +
            "<where>" +
            " u.imie = #{phone} " +
            "<if test='content !=null '>" +
            "and d.content LIKE CONCAT('%',#{content},'%') " +
            "</if>" +
            "<if test='start !=null '>" +
            "and d.publish_date between  #{start} and  #{end}   " +
            "</if>" +
            "</where>" +
            "ORDER BY  d.publish_date  DESC " +
            "limit #{pageBegainNum},#{pageSize} " +
            "</script>"
    )
    @Results({
            @Result(property = "publishDate", column = "publish_date"),
            @Result(property = "typeLabel", column = "type_label"),
            @Result(property = "warnLevel", column = "warn_level"),
            @Result(property = "sourceName", column = "source_name"),
            @Result(property = "riskValue", column = "risk_value")
    })
    List<DataInfo> searchDataList(@Param(value = "phone") String phone, @Param(value = "content") String content,
                                  @Param(value = "start") String start, @Param(value = "end") String end,
                                  @Param(value = "pageBegainNum") int pageBegainNum, @Param(value = "pageSize") int pageSize);

    /**
     * 通过ID查询详情
     *
     * @return com.yunce.cloud.yuncecloudsearch.entity.DataInfo
     * @Author TangChao
     * @Date 17:34 2019/1/18 0018
     * @Param [id]
     **/
    @Select("select " +
            " t.id,t.title,t.author,t.content,t.appraise,t.type_label,t.warn_level,t.risk_value,t.publish_date,t.url " +
            "  from t_data_info as t where t.id = #{id} ")
    @Results({
            @Result(property = "typeLabel", column = "type_label"),
            @Result(property = "warnLevel", column = "warn_level"),
            @Result(property = "riskValue", column = "risk_value"),
            @Result(property = "publishDate", column = "publish_date")
    })
    DataInfo searchDataById(Long id);

    /**
     * @param userId
     * @param id
     * @return 根据用户id和数据id，查询任务
     */
    @Select(" SELECT sr.search_keys FROM t_user_task ut INNER JOIN t_task_data td ON ut.real_id=td.search_id INNER JOIN " +
            " t_real_task rt ON td.search_id=rt.id INNER JOIN t_search_task sr ON rt.search_id =sr.id WHERE td.id=#{id} " +
            " AND ut.user_id=#{userId} ")
    List<String> findSearchKeysByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);


    /**
     * 修改该条信息为已读
     *
     * @return boolean
     * @Author TangChao
     * @Date 17:34 2019/1/18 0018
     * @Param [id]
     **/
    @Update(" update t_task_data as t  INNER JOIN  t_user_task as u ON t.search_id = u.real_id   " +
            "  set  t.read = 1  where u.user_id = #{userId}   and t.id = #{id} ")
    boolean updataReadById(@Param(value = "id") Long id, @Param(value = "userId") Long userId);

    @Select("select  rt.id,st.search_keys  from  t_search_task  as  st " +
            "INNER JOIN   t_real_task  as rt  ON  st.id = rt.search_id " +
            "INNER JOIN   t_user_task as  ut  ON  rt.id = ut.real_id  " +
            " where  ut.user_id = #{userId} " +
            "  ORDER BY  rt.create_time  DESC   ")
    @Results({
            @Result(property = "searchKeys", column = "search_keys")
    })
    List<SearchDto> selectSimilarDataByUserId(@Param(value = "userId") Long userId);

    @Select(" SELECT s.search_keys AS searchKeys ,t.real_id AS realId FROM t_user_task t INNER JOIN " +
            " t_real_task r ON t.real_id=r.`id` INNER JOIN t_search_task s ON r.`search_id`=s.`id` " +
            " WHERE t.user_id=#{userId}")
    List<Map<String, Object>> getKeyAndRealId(@Param("userId") Long userId);

    @Select("select real_id from t_user_task where  user_id = #{userId}")
    List<Long> selectRealIdByUserId(@Param(value = "userId") Long userId);

    @Select("select t.id, t.search_id as  realId  from t_task_data  as  t    INNER JOIN t_user_task as r  ON  t.search_id = r.real_id    where  r.user_id = #{userId}  and  t.id = #{id} ")
    List<Map> selectListRead(@Param(value = "id") Long id, @Param(value = "userId") Long userId);

    @Select(" SELECT u.`real_id` AS realId,s.`search_keys` AS searchKeys FROM t_user_task u INNER JOIN " +
            " t_real_task  t ON u.`real_id`=t.`id` INNER JOIN t_search_task  s ON t.`search_id`=s.`id` " +
            " WHERE u.`user_id`=#{userId} ORDER BY t.`create_time` DESC")
    List<Map<String, Object>> findKeyAndRealId(@Param("userId") Long userId);

    @Select("select read_ids as readIds from  t_user_read where user_id=#{userId}")
    String findUserReadInfo(@Param("userId") Long userId);

    @Insert(" replace into t_user_read(user_id, read_ids) values(#{userId},#{readIds}); ")
    Integer insertUserReadData(@Param("userId") Long userId, @Param("readIds") String readIds);

    @Select("SELECT real_id FROM t_user_task WHERE user_id=#{userId}")
    List<Long> findRealIdListByUserId(@Param("userId") Long userId);


    @Delete("DELETE FROM t_user_read WHERE user_id=#{userId}")
    Integer deleteReadByUserId(@Param("userId") Long userId);
}
