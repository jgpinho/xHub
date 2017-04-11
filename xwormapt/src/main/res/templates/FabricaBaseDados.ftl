package ${pacote};

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import com.extraware.xwormapi.GestorBasedados;
import com.extraware.xwormapi.GestorTabela;
import com.extraware.xwormapi.api.FabricaBaseDados;

public class ${nomeFabrica} implements FabricaBaseDados {

private static final String NOME_BD = "${nomeBD}";
private static final int VERSAO_BD = ${versaoBD};
private static final GestorTabela[] GESTORES_TABELA = new GestorTabela[] {
<#list gestoresBasedados as gbd>
new ${gbd}()<#if gbd_has_next>,</#if>
</#list>
};
private static GestorBasedados instancia;

/**
* Método para obter uma instância única (singleton) do GestorBasedados por aplicação, evitando assim problemas
* concorrenciais.
*
* @param contexto Contexto aplicacional
* @return Instancia GestorBasedados.
*/
public static GestorBasedados getGestorBasedados(Context contexto) {
if (instancia==null) {
// in case this is called from an AsyncTask or other thread
synchronized(${nomeFabrica}.class) {
if (instancia == null)
instancia = new ${classeGestorBasedados}(
contexto.getApplicationContext(),
new ${nomeFabrica}());
}
}
return instancia;
}

public String getNome() {
return NOME_BD;
}

public int getVersao() {
return VERSAO_BD;
}

public GestorTabela[] getGestoresTabela() {
return GESTORES_TABELA;
}

private ${nomeFabrica}() {
// não instanciável
}

}
