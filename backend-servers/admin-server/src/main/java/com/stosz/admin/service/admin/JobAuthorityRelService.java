package com.stosz.admin.service.admin;

import com.stosz.admin.ext.enums.JobAuthorityRelEnum;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.Job;
import com.stosz.admin.ext.model.JobAuthorityRel;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IJobAuthorityRelService;
import com.stosz.admin.ext.service.IUserDepartmentRelService;
import com.stosz.admin.mapper.admin.DepartmentMapper;
import com.stosz.admin.mapper.admin.JobAuthorityRelMapper;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.product.ext.model.UserDepartmentRel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/14]
 */
@Service
public class JobAuthorityRelService implements IJobAuthorityRelService{

    @Resource
    private JobAuthorityRelMapper mapper;

    @Autowired
    private IUserDepartmentRelService userDepartmentRelService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Resource
    private JobService jobService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void insert(JobAuthorityRel jobAuthorityRel){
        Assert.notNull(jobAuthorityRel,"绑定职位与权限的关系不能为空！");
        JobAuthorityRel jobAuthorityRel1  = mapper.getByJobId(jobAuthorityRel.getJobId());
        if(jobAuthorityRel1 != null){
            delete(jobAuthorityRel1.getId());
        }
        UserDto user = ThreadLocalUtils.getUser();
        jobAuthorityRel.setCreatorId(user.getId());
        jobAuthorityRel.setCreator(user.getLastName());
        int i = mapper.insert(jobAuthorityRel);
        Assert.isTrue(i==1,"绑定职位与权限关系"+jobAuthorityRel+"失败！");
        logger.info("绑定职位与权限关系{}成功！",jobAuthorityRel);
    }

    public void delete(Integer id){
        Assert.notNull(id,"职位与权限关系删除id不允许为空！");
        JobAuthorityRel jobAuthorityRel = mapper.getById(id);
        Assert.notNull(jobAuthorityRel,"id为【"+id+"】的职位权限关系在数据库中不存在！");
        int i = mapper.delete(id);
        Assert.isTrue(i==1,"删除职位与权限关系失败！");
        logger.info("删除职位与权限关系{}成功！",jobAuthorityRel);
    }

    public RestResult findByJobId(Integer jobId){
        Assert.notNull(jobId,"职位id不允许为空！");
        JobAuthorityRel jobAuthorityRel = mapper.getByJobId(jobId);
        if(jobAuthorityRel == null){
            jobAuthorityRel = new JobAuthorityRel();
            jobAuthorityRel.setAuthority(JobAuthorityRelEnum.myself.name());
        }
        RestResult result = new RestResult();
        result.setItem(jobAuthorityRel);
        return result;
    }

    @Override
    public Boolean hasSelfAuthority(UserDto userDto) {
        Assert.notNull(userDto, "用户不允许为空！");
        Integer userId = userDto.getId();
        List<JobAuthorityRel> jobAuthorityRelList = mapper.findByUser(userId);
        if (jobAuthorityRelList != null || jobAuthorityRelList.size() != 0) {
            return jobAuthorityRelList.stream().anyMatch(e->e.getJobAuthorityRelEnum() == JobAuthorityRelEnum.myself);
        }
        return false;
    }

    public List<JobAuthorityRel> findByUser(UserDto user) {
        Assert.notNull(user, "用户不允许为空！");
        Integer userId = user.getId();
        List<JobAuthorityRel> jobAuthorityRelList = mapper.findByUser(userId);
        if (jobAuthorityRelList == null || jobAuthorityRelList.size() == 0) {
            jobAuthorityRelList = new ArrayList<>();
            List<Job>  jobList = jobService.findByUserId(userId);
            for (Job job: jobList) {
                JobAuthorityRel jobAuthorityRel = new JobAuthorityRel();
                jobAuthorityRel.setJobId(job.getId());
                jobAuthorityRel.setAuthority(JobAuthorityRelEnum.myself.name());
                jobAuthorityRelList.add(jobAuthorityRel);
            }
        }
        return jobAuthorityRelList;
    }

    /**
     * 获取该用户具有的部门权限
     * @param user
     * @return 部门对应的id
     */
    public List<Integer> findDepartmentIdsByUser(UserDto user){
        Set<Integer> departmentIds = new HashSet<>();
        //1.获取用户Job对应的JobAuthority
        List<JobAuthorityRel> jobAuthorityRelList = findByUser(user);
        for(JobAuthorityRel rel : jobAuthorityRelList){
            if(JobAuthorityRelEnum.fromName(rel.getAuthority()).ordinal() == JobAuthorityRelEnum.all.ordinal()){// 全公司
                List<Department> departmentList = departmentMapper.selectAllDepartment();
                Set set = departmentList.stream().map(l -> l.getId()).collect(Collectors.toSet());
                departmentIds.addAll(set);
                break;
            }else if(JobAuthorityRelEnum.fromName(rel.getAuthority()).ordinal() == JobAuthorityRelEnum.myDepartment.ordinal()){//用户所属部门
                List<Department> departmentList = departmentMapper.getDepartmentByNo(user.getDeptNo());
                Set set = departmentList.stream().map(l -> l.getId()).collect(Collectors.toSet());
                departmentIds.addAll(set);
            }
        }

        //2.通过UserDepartmentRef获取用户的UserDepartment
        List<UserDepartmentRel> userDepartmentRelList = userDepartmentRelService.findByUserId(user.getId(),true);
        for(UserDepartmentRel rel: userDepartmentRelList){
            List<Department> departmentList = departmentMapper.getDepartmentByNo(rel.getDepartmentNo());
            Set set = departmentList.stream().map(l -> l.getId()).collect(Collectors.toSet());
            departmentIds.addAll(set);
        }

        //3.取他们并集作为用户具有的部门权限
        return new ArrayList<>(departmentIds);

    }


    @Override
    public List<Department> findDepartmentByUser(UserDto userDto) {
        List<Integer> idList =  findDepartmentIdsByUser(userDto);
        if(com.stosz.plat.utils.CollectionUtils.isNotNullAndEmpty(idList)){
            return departmentMapper.findByIds(idList);
        }
        return null;
    }

    @Override
    public JobAuthorityRel getByUser(Integer userId) {
        Assert.notNull(userId, "用户不允许为空！");
        List<JobAuthorityRel> jobAuthorityRelList = mapper.findByUser(userId);
        JobAuthorityRel maxJob = null;
        if (jobAuthorityRelList != null || jobAuthorityRelList.size() != 0) {
            for (JobAuthorityRel jobAuthorityRel: jobAuthorityRelList) {
                if(maxJob == null ||jobAuthorityRel.getJobAuthorityRelEnum().ordinal()<maxJob.getJobAuthorityRelEnum().ordinal()){
                    maxJob = jobAuthorityRel;
                }
            }
        }
        return maxJob;
    }
}
