package com.idiots.authentication.util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author devil-idiots
 * Date 2022-12-02
 * Description
 */
public class GeneratorUtil {
    private static final String URL ="jdbc:mysql://192.168.73.131:3306/idiots-authentication";
    private static final String USERNAME ="root";
    private static final String PASSWORD ="Tv75aYT8@";
    private static final String MAPPER_OUTPUT_PATH ="D:\\mapper";
    private static final String XML_OUTPUT_PATH ="D:\\mapper";
    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> {
                    builder.author("devil-idiots") // 设置作者
                            .outputDir(MAPPER_OUTPUT_PATH); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.unnet.traffic") // 设置父包名
                            .moduleName("lvs") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, XML_OUTPUT_PATH)); // 设置mapperXml生成路径
                })
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("create_time", FieldFill.INSERT)
                        ).build())
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
