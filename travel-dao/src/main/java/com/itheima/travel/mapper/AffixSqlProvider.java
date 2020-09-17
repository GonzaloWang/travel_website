package com.itheima.travel.mapper;

import com.itheima.travel.pojo.Affix;
import com.itheima.travel.pojo.AffixExample.Criteria;
import com.itheima.travel.pojo.AffixExample.Criterion;
import com.itheima.travel.pojo.AffixExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class AffixSqlProvider {

    public String countByExample(AffixExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("tab_affix");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(AffixExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("tab_affix");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Affix record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tab_affix");
        
        if (record.getBusinessId() != null) {
            sql.VALUES("business_id", "#{businessId,jdbcType=BIGINT}");
        }
        
        if (record.getBusinessType() != null) {
            sql.VALUES("business_type", "#{businessType,jdbcType=VARCHAR}");
        }
        
        if (record.getSuffix() != null) {
            sql.VALUES("suffix", "#{suffix,jdbcType=VARCHAR}");
        }
        
        if (record.getFileName() != null) {
            sql.VALUES("file_name", "#{fileName,jdbcType=VARCHAR}");
        }
        
        if (record.getPathUrl() != null) {
            sql.VALUES("path_url", "#{pathUrl,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExample(AffixExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("business_id");
        sql.SELECT("business_type");
        sql.SELECT("suffix");
        sql.SELECT("file_name");
        sql.SELECT("path_url");
        sql.FROM("tab_affix");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Affix record = (Affix) parameter.get("record");
        AffixExample example = (AffixExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("tab_affix");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getBusinessId() != null) {
            sql.SET("business_id = #{record.businessId,jdbcType=BIGINT}");
        }
        
        if (record.getBusinessType() != null) {
            sql.SET("business_type = #{record.businessType,jdbcType=VARCHAR}");
        }
        
        if (record.getSuffix() != null) {
            sql.SET("suffix = #{record.suffix,jdbcType=VARCHAR}");
        }
        
        if (record.getFileName() != null) {
            sql.SET("file_name = #{record.fileName,jdbcType=VARCHAR}");
        }
        
        if (record.getPathUrl() != null) {
            sql.SET("path_url = #{record.pathUrl,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("tab_affix");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("business_id = #{record.businessId,jdbcType=BIGINT}");
        sql.SET("business_type = #{record.businessType,jdbcType=VARCHAR}");
        sql.SET("suffix = #{record.suffix,jdbcType=VARCHAR}");
        sql.SET("file_name = #{record.fileName,jdbcType=VARCHAR}");
        sql.SET("path_url = #{record.pathUrl,jdbcType=VARCHAR}");
        
        AffixExample example = (AffixExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Affix record) {
        SQL sql = new SQL();
        sql.UPDATE("tab_affix");
        
        if (record.getBusinessId() != null) {
            sql.SET("business_id = #{businessId,jdbcType=BIGINT}");
        }
        
        if (record.getBusinessType() != null) {
            sql.SET("business_type = #{businessType,jdbcType=VARCHAR}");
        }
        
        if (record.getSuffix() != null) {
            sql.SET("suffix = #{suffix,jdbcType=VARCHAR}");
        }
        
        if (record.getFileName() != null) {
            sql.SET("file_name = #{fileName,jdbcType=VARCHAR}");
        }
        
        if (record.getPathUrl() != null) {
            sql.SET("path_url = #{pathUrl,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, AffixExample example, boolean includeExamplePhrase) {
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