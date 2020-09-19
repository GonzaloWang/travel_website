package com.itheima.travel.mapper;

import com.itheima.travel.pojo.Route;
import com.itheima.travel.pojo.RouteExample.Criteria;
import com.itheima.travel.pojo.RouteExample.Criterion;
import com.itheima.travel.pojo.RouteExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class RouteSqlProvider {

    public String countByExample(RouteExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("tab_route");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(RouteExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("tab_route");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Route record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tab_route");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getRouteName() != null) {
            sql.VALUES("route_name", "#{routeName,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            sql.VALUES("price", "#{price,jdbcType=DECIMAL}");
        }
        
        if (record.getRouteIntroduce() != null) {
            sql.VALUES("route_Introduce", "#{routeIntroduce,jdbcType=VARCHAR}");
        }
        
        if (record.getFlag() != null) {
            sql.VALUES("flag", "#{flag,jdbcType=CHAR}");
        }
        
        if (record.getIsThemeTour() != null) {
            sql.VALUES("is_theme_tour", "#{isThemeTour,jdbcType=CHAR}");
        }
        
        if (record.getAttentionCount() != null) {
            sql.VALUES("attention_count", "#{attentionCount,jdbcType=INTEGER}");
        }
        
        if (record.getCategoryId() != null) {
            sql.VALUES("category_id", "#{categoryId,jdbcType=BIGINT}");
        }
        
        if (record.getSellerId() != null) {
            sql.VALUES("seller_id", "#{sellerId,jdbcType=BIGINT}");
        }
        
        if (record.getCreatedTime() != null) {
            sql.VALUES("created_time", "#{createdTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(RouteExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("route_name");
        sql.SELECT("price");
        sql.SELECT("route_Introduce");
        sql.SELECT("flag");
        sql.SELECT("is_theme_tour");
        sql.SELECT("attention_count");
        sql.SELECT("category_id");
        sql.SELECT("seller_id");
        sql.SELECT("created_time");
        sql.FROM("tab_route");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Route record = (Route) parameter.get("record");
        RouteExample example = (RouteExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("tab_route");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getRouteName() != null) {
            sql.SET("route_name = #{record.routeName,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            sql.SET("price = #{record.price,jdbcType=DECIMAL}");
        }
        
        if (record.getRouteIntroduce() != null) {
            sql.SET("route_Introduce = #{record.routeIntroduce,jdbcType=VARCHAR}");
        }
        
        if (record.getFlag() != null) {
            sql.SET("flag = #{record.flag,jdbcType=CHAR}");
        }
        
        if (record.getIsThemeTour() != null) {
            sql.SET("is_theme_tour = #{record.isThemeTour,jdbcType=CHAR}");
        }
        
        if (record.getAttentionCount() != null) {
            sql.SET("attention_count = #{record.attentionCount,jdbcType=INTEGER}");
        }
        
        if (record.getCategoryId() != null) {
            sql.SET("category_id = #{record.categoryId,jdbcType=BIGINT}");
        }
        
        if (record.getSellerId() != null) {
            sql.SET("seller_id = #{record.sellerId,jdbcType=BIGINT}");
        }
        
        if (record.getCreatedTime() != null) {
            sql.SET("created_time = #{record.createdTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("tab_route");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("route_name = #{record.routeName,jdbcType=VARCHAR}");
        sql.SET("price = #{record.price,jdbcType=DECIMAL}");
        sql.SET("route_Introduce = #{record.routeIntroduce,jdbcType=VARCHAR}");
        sql.SET("flag = #{record.flag,jdbcType=CHAR}");
        sql.SET("is_theme_tour = #{record.isThemeTour,jdbcType=CHAR}");
        sql.SET("attention_count = #{record.attentionCount,jdbcType=INTEGER}");
        sql.SET("category_id = #{record.categoryId,jdbcType=BIGINT}");
        sql.SET("seller_id = #{record.sellerId,jdbcType=BIGINT}");
        sql.SET("created_time = #{record.createdTime,jdbcType=TIMESTAMP}");
        
        RouteExample example = (RouteExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Route record) {
        SQL sql = new SQL();
        sql.UPDATE("tab_route");
        
        if (record.getRouteName() != null) {
            sql.SET("route_name = #{routeName,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            sql.SET("price = #{price,jdbcType=DECIMAL}");
        }
        
        if (record.getRouteIntroduce() != null) {
            sql.SET("route_Introduce = #{routeIntroduce,jdbcType=VARCHAR}");
        }
        
        if (record.getFlag() != null) {
            sql.SET("flag = #{flag,jdbcType=CHAR}");
        }
        
        if (record.getIsThemeTour() != null) {
            sql.SET("is_theme_tour = #{isThemeTour,jdbcType=CHAR}");
        }
        
        if (record.getAttentionCount() != null) {
            sql.SET("attention_count = #{attentionCount,jdbcType=INTEGER}");
        }
        
        if (record.getCategoryId() != null) {
            sql.SET("category_id = #{categoryId,jdbcType=BIGINT}");
        }
        
        if (record.getSellerId() != null) {
            sql.SET("seller_id = #{sellerId,jdbcType=BIGINT}");
        }
        
        if (record.getCreatedTime() != null) {
            sql.SET("created_time = #{createdTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, RouteExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}