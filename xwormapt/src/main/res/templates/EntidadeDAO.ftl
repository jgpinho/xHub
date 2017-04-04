package ${pacoteDAO};

import android.content.Context;
import com.extraware.xwormapi.GestorBasedados;
import com.extraware.xwormapi.GestorTabela;
<#list importacoes as importacao>
import ${importacao};
</#list>

/**
* GENERATED CODE
*/
public class ${nomeDAO} extends ${nomeBasedadosDAO}<${nomeEntidade}>{

@Override
public GestorBasedados getGestorBasedados(Context contexto) {
    return ${fabricaBasedados}.getGestorBasedados(contexto);
}

@Override
@SuppressWarnings("rawtypes")
public GestorTabela getGestorTabela() {
    return new ${classeGestorTabela}();
}

/**
* @see SQLiteDao#SQLiteDao(Context)
*/
public ${nomeDAO}(Context contexto) {
    super(contexto);
}

}