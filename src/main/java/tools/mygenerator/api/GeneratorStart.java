package tools.mygenerator.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.mygenerator.config.Context;
import tools.mygenerator.config.GeneratorRegistry;
import tools.mygenerator.config.JDBCConnectionConfiguration;
import tools.mygenerator.config.JavaModelGeneratorConfiguration;
import tools.mygenerator.config.PluginConfiguration;
import tools.mygenerator.config.SqlMapGeneratorConfiguration;
import tools.mygenerator.config.TableChooseConfiguration;

/** 
* 生成开始方法
* @author 作者 : zyq
* 创建时间：2017年3月11日 上午9:33:59 
* @version 
*/
public class GeneratorStart {
	public static void main(String[] args) throws Exception {
		Context context=new Context();
		List<String> warnings=new ArrayList<String>();		
		Date d=new Date();
		
		
		/**
		 * context 配置
		 */
		//设置jdbc链接配置
    	JDBCConnectionConfiguration c=new JDBCConnectionConfiguration();
//		c.setConnectionURL("jdbc:mysql://120.25.220.63:3306/fsstp_db");
//		c.setDriverClass("com.mysql.jdbc.Driver");
//		c.setPassword("life12345");
//		c.setUserId("fsstp_admin");
		c.setConnectionURL("jdbc:mysql://localhost:3306/ngwf_he_dev");
		c.setDriverClass("com.mysql.jdbc.Driver");
		c.setUserId("root");
		c.setPassword("admin");
		
		//设置选择表
		TableChooseConfiguration tc=new TableChooseConfiguration();
		tc.setCatalog("ngwf_he_dev");
		tc.setSchema("");
		tc.setNameLike("%");
		
		//设置指定表
		Set<String> chooseTables=new HashSet<String>();
		//chooseTables.add("t_wf_workitem");
		//chooseTables.add("t_wf_processinfo");
		chooseTables.add("t_sr_pro_csvc_extend");
		tc.setChooseTables(chooseTables);
		//设置忽略表
//		Set<String> ignoreTables=new HashSet<String>();
//		ignoreTables.add("test");
//		tc.setIgnoredTables(ignoreTables);
		
		context.setJdbcConnectionConfiguration(c);
		context.setTableChooseConfiguration(tc);
//		context.setCommentGenerator(new DefaultCommentGenerator());
		
		
		//以下设置生成代码相关配置
		//======================
		//======配置java实体类生成规则
		JavaModelGeneratorConfiguration javaModelGeneratorConfiguration=new JavaModelGeneratorConfiguration();
		javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
		javaModelGeneratorConfiguration.setTargetPackage("tools.test.entity");
		javaModelGeneratorConfiguration.setTargetProject("D:/hhh");
		//配置是生成实体类名否使用驼峰式命名("Y"/"N")
		javaModelGeneratorConfiguration.addProperty("beanNameCamelCaseEnable", "Y");
		javaModelGeneratorConfiguration.addProperty("beanFieldNameCamelCaseEnable", "Y");
		
		context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
		
		//======配置sqlMapper生成规则
		SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration=new SqlMapGeneratorConfiguration();
		sqlMapGeneratorConfiguration.setTargetPackage("tools.test.mapper");
		sqlMapGeneratorConfiguration.setTargetProject("D:/hhh");
		//配置所有表生成sqlMapper时使用的别名
		sqlMapGeneratorConfiguration.addProperty("tableAlias", "tb5");
		//配置是否生成tableName sql("Y"/"N")
		sqlMapGeneratorConfiguration.addProperty("generateTableNameEnable", "Y");
		//配置是否生成tableColumns sql("Y"/"N")
		sqlMapGeneratorConfiguration.addProperty("generateColumnsEnable", "Y");
		//配置是否生成查询所有列 sql("Y"/"N")
		sqlMapGeneratorConfiguration.addProperty("generateSelectAllEnable", "Y");
		//配置是否生成普通插入 sql("Y"/"N")
		sqlMapGeneratorConfiguration.addProperty("generateInsertEnable", "Y");
		//配置Mapper文件 <select>节点参数是否使用Map类的形式 ("Y"/"N")
		sqlMapGeneratorConfiguration.addProperty("mapParamEnable", "Y");
		context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
		
		
		//======配置使用哪些生成器
		List<String> generators=new ArrayList<String>();
		generators.add(GeneratorRegistry.javaEntity);
		generators.add(GeneratorRegistry.mybatis3Mapper);
		context.setGenerators(generators);
		
		//======配置使用的插件
		List<PluginConfiguration> pluginConfigurations=new ArrayList<PluginConfiguration>();
		//添加toStringPlugin插件
		PluginConfiguration toStringPlugin=new PluginConfiguration();
		toStringPlugin.setConfigurationType("tools.mygenerator.plugins.ToStringPlugin");
		pluginConfigurations.add(toStringPlugin);
		context.setPluginConfigurations(pluginConfigurations);
		
		//======配置生成的文件是否覆盖
		context.setOverwriteEnabled(true);
		
		
		
		MyGenerator my=new MyGenerator(context, warnings);
		my.generate();
		for (String warning : warnings) {
			System.out.println(warning);
		}
		
	}

}
