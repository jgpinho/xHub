package ${pacoteDAO};

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import com.extraware.xwormapi.query.Query;
import com.extraware.xwormapi.GestorTabela;
import java.util.Map;
import java.util.HashMap;
<#list importacoes as importacao>
import ${importacao};
</#list>

/**
* GENERATED CODE
*
* Esta classe contem o SQL DDL de uma entidade / tabela.
* Estes métodos não estão incluídos na classe EntidadeDAO porque eles têm de ser executados antes do DAO poder ser
* instanciado, e são métodos instanciados em vez de estáticos para poderem ser re-escritos de forma segura.
*/
public class ${nomeGestorTabela} extends GestorTabeça<${nomeEntidade}> {

public enum Colunas implements GestorTabela.Coluna {
<#list campos as campo>
${campo.nome}<#if campo_has_next>,</#if>
</#list>;
public String asc() {
return this.nome() + " ASC";
}
public String desc() {
return this.nome() + " DESC";
}
}

@Override
public String getNomeTabela() {
return "${nomeTabela}";
}

@Override
public Coluna[] getColunas() {
return Colunas.values();
}

@Override
public long getId(${nomeEntidade} obj) {
return obj.${campoId.getter}();
}

@Override
public void setId(${nomeEntidade} obj, long id) {
obj.${campoId.setter}(id);
}

@Override
public Coluna getColunaId() {
return Colunas._id;
}

@Override
public String criarSQL() {
return
"CREATE TABLE IF NOT EXISTS ${nomeTabela}(" +
<#list campos as campo>
"${campo.nome} ${campo.tipoSQL}<#if !campo.nullable> NOT NULL</#if><#if campo_has_next>,</#if>" +
</#list>
")";
}

@Override
public String removerSQL() {
return "DROP TABLE IF EXISTS ${nomeTabela}";
}

@Override
public String atualizarSQL(int versaoAntiga, int versaoNova) {
return null;
}

@Override
public String[] getValoresLinha(Cursor c) {
String[] valores = new String[this.getColunas().length];
String[] valoresPorDefeito = getValoresPorDefeito();
int colIdx; // posição do campo da entidade no cursor
<#list campos as campo>
    <#if campo.enum>
    colIdx = c.getColumnIndex("${campo.nome}"); values[${campo_indice}] = (colIdx < 0) ? defaultValues[${campo_indice}] : getStringOrNull(c, colIdx);
    <#else>
    colIdx = c.getColumnIndex("${campo.nome}"); values[${campo_indice}] = (colIdx < 0) ? defaultValues[${campo_indice}] : ${campo.nomeConversor}.LER.toString(get${campo.tipoLigacao}OrNull(c, colIdx));
    </#if>
</#list>
return values;
}

@Override
public void ligacaoValoresLinha(InsertHelper gestorInsercao, String[] valoresLinha) {
<#list campos as campo>
    <#if campo.enum>
    if (valoresLinha[${campo_index}] == null) gestorInsercao.bindNull(${campo_index+1}); else gestorInsercao.bind(${campo_index+1}, valoresLinha[${campo_index}]);
    <#else>
    if (valoresLinha[${campo_index}] == null) gestorInsercao.bindNull(${campo_index+1}); else gestorInsercao.bind(${campo_index+1}, ${campo.nomeConversor}.LER.fromString(valoresLinha[${campo_index}]));
    </#if>
</#list>
}

@Override
public String[] getValoresPorDefeito() {
String[] valores = new String[getColunas().length];
${nomeEntidade} objeto = new ${nomeEntidade}();
<#list campos as campo>
    <#if campo.enum>
    valores[${campo_index}] = (objeto.${campo.getter}() == null) ? null : objeto.${campo.getter}().nome();
    <#else>
    valores[${campo_index}] = ${campo.nomeConversor}.LER.toString(${campo.nomeConversor}.LER.paraSQL(objeto.${campo.getter}()));
    </#if>
</#list>
return valores;
}

@Override
public ${nomeEntidade} novaInstancia(Cursor c) {
${nomeEntidade} obj = new ${nomeEntidade}();
<#list campos as campo>
    <#if campo.tipoJava == "byte[]">
    obj.${campo.setter}(c.getBlob(${campo_index}));
    <#elseif campo.tipoJava == "boolean">
    obj.${campo.setter}(c.getInt(${campo_index}) == 1 ? true : false);
    <#elseif campo.tipoJava == "byte">
    obj.${campo.setter}((byte) c.getShort(${campo_index}));
    <#elseif campo.tipoJava == "char">
    obj.${campo.setter}((char) c.getInt(${campo_index}));
    <#elseif campo.tipoJava == "double">
    obj.${campo.setter}(c.getDouble(${campo_index}));
    <#elseif campo.tipoJava == "float">
    obj.${campo.setter}(c.getFloat(${campo_index}));
    <#elseif campo.tipoJava == "int">
    obj.${campo.setter}(c.getInt(${campo_index}));
    <#elseif campo.tipoJava == "long">
    obj.${campo.setter}(c.getLong(${campo_index}));
    <#elseif campo.tipoJava == "short">
    obj.${campo.setter}(c.getShort(${campo_index}));
    <#elseif campo.tipoJava == "java.lang.String">
    obj.${campo.setter}(c.getString(${campo_index}));
    <#elseif campo.enum>
    obj.${campo.setter}(c.isNull(${campo_index}) ? null : ${campo.tipoJava}.valueOf(c.getString(${campo_index})));
    <#else>
    obj.${campo.setter}(${campo.nomeConversor}.LER.fromSql(get${campo.tipoLigacao}OrNull(c, ${campo_index})));
    </#if>
</#list>
return obj;
}

@Override
public ContentValues getValoresEditaveis(${nomeEntidade} obj) {
ContentValues cv = new ContentValues();
<#list campos as campo>
    <#if campo.tipoJava == "byte[]">
    cv.put("${campo.nome}", obj.${campo.getter}());
    <#elseif campo.tipoJava == "boolean">
    cv.put("${campo.nome}", obj.${campo.getter}() ? 1 : 0);
    <#elseif campo.tipoJava == "byte">
    cv.put("${campo.nome}", (short) obj.${campo.getter}());
    <#elseif campo.tipoJava == "char">
    cv.put("${campo.nome}", (int) obj.${campo.getter}());
    <#elseif campo.tipoJava == "double">
    cv.put("${campo.nome}", obj.${campo.getter}());
    <#elseif campo.tipoJava == "float">
    cv.put("${campo.nome}", obj.${campo.getter}());
    <#elseif campo.tipoJava == "int">
    cv.put("${campo.nome}", obj.${campo.getter}());
    <#elseif campo.tipoJava == "long">
    cv.put("${campo.nome}", obj.${campo.getter}());
    <#elseif campo.tipoJava == "short">
    cv.put("${campo.nome}", obj.${campo.getter}());
    <#elseif campo.tipoJava == "java.lang.String">
    cv.put("${campo.nome}", obj.${campo.getter}());
    <#elseif campo.enum>
    cv.put("${campo.colName}", obj.${campo.getter}() == null ? null : obj.${campo.getter}().name());
    <#else>
    cv.put("${campo.colName}", ${campo.nomeConversor}.LER.toSql(obj.${campo.getter}()));
    </#if>
</#list>
return cv;
}

@Override
public Query criarFiltro(Query filtro, ${nomeEntidade} obj) {
${nomeEntidade} objetoPorDefeito = new ${nomeEntidade}();

// Incluir campos na query se eles diferem dos objetos por defeito
<#list campos as campo>
if (obj.${campo.getter}() != objetoPorDefeito.${campo.getter}())
    <#if campo.enum>
    filtro = filtro.eq(Colunas.${campo.nome}, obj.${campo.getter}() == null ? null : obj.${campo.getter}().nome());
    <#else>
    filtro = filtro.eq(Colunas.${campo.nome}, ${campo.nomeConversor}.LER.paraSQL(obj.${campo.getter}()));
    </#if>
</#list>
return filtro;
}

}