package zxx.pri.core.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author : liyan
 * @Description : TODO
 * @date : 2019-06-03 14:11
 **/
@Mapper
public interface TaskDataV2Mapper {

    /**
     * 根据任务查询所有的数据id
     */
    @Select(" <script>" +
            " SELECT t.id from t_task_data t  WHERE  t.search_id in " +
            " <foreach collection='realIdList'  item='item' open='(' separator=',' close=')'  > " +
            " #{item} " +
            " </foreach> " +
            " </script>")
    List<Long> findAllIdList(@Param("realIdList") List<Long> realIdList);

    /**
     * 根据任务查询所有的数据id
     */
    @Select(" <script>" +
            " SELECT search_id from t_real_task  WHERE  t.id in " +
            " <foreach collection='realIdList'  item='item' open='(' separator=',' close=')'  > " +
            " #{item} " +
            " </foreach> " +
            " </script>")
    List<Long> findAllSearchIdList(@Param("realIdList") List<Long> realIdList);

    /**
     * 根据任务id查询数据
     *
     * @param ids
     * @return
     */
    @Select(" <script>" +
            " SELECT d.id as id,t.read ,d.author ,d.forward,d.publish_date as publishDate,d.zan_total as zanTotal,d.source_name as sourceName," +
            " d.title ,d.type ,d.click ,d.content ,d.url ,d.repeat_total as repeatTotal ,d.appraise,d.warn,d.warn_level as warnLevel," +
            " d.warn_label as warnLabel,d.risk_value as riskValue,d.type_label as typeLabel,t.`search_id` as realId" +
            " from t_data_info d inner JOIN t_task_data t ON d.`id`=t.`id` " +
            " WHERE  d.id in " +
            " <foreach collection='ids'  item='item' open='(' separator=',' close=')'  > " +
            " #{item} " +
            " </foreach> " +
            " </script>")
    List<Map<String, Object>> findDataByidsV2(@Param("ids") List<Long> ids);

    /**
     * 根据任务id查询数据
     *
     * @return
     */
    @Select(" SELECT d.id as id,d.author ,d.forward,d.publish_date as publishDate,d.zan_total as zanTotal,d.source_name as sourceName," +
            " d.title ,d.type ,d.click ,d.content ,d.url ,d.repeat_total as repeatTotal ,d.appraise,d.warn,d.warn_level as warnLevel," +
            " d.warn_label as warnLabel,d.risk_value as riskValue,d.type_label as typeLabel,r.id as realId,s.search_keys keyWords " +
            " from t_data_info d inner JOIN t_task_data t ON d.`id`=t.`id` " +
            " inner join t_real_task r on r.search_id=t.search_id " +
            " inner join t_search_task s on s.id=r.search_id " +
            " WHERE r.id=#{realId} ")
    List<Map<String, Object>> findDataByidsV3(@Param("realId") Long realId);

    @Select(" SELECT t.id FROM t_task_data t inner join t_real_task r on t.search_id=r.search_id WHERE r.id= #{realId} ")
    List<Long> findTaskDataIdByDataIdV2(@Param("realId") Long realId);

    @Select(" select real_id from t_user_task where user_id=#{userId} ")
    List<Long> getRealIdsByUserId(@Param("userId") Long userId);

}
