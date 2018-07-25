package hpscore.repository;

import hpscore.domain.Works;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/06
 * Time: 13:36
 */
/*
* Integer 是id 的类型*/
public interface WorksRepository extends JpaRepository<Works,Integer>{
    List<Works> findByYear(int year);
    List<Works> findByModelAndYear(String model,int year);
    Works findByNameAndModelAndYear(String name,String model,int year);
    Works findByCodeAndModelAndYear(String code,String model,int year);

}
