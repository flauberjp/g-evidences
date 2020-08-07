package io.github.flauberjp;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import io.github.flauberjp.forms.component.ProjetosGitDetectadosTableComponent;
import io.github.flauberjp.model.GitDir;
import io.github.flauberjp.util.Util;

public class HistoryReflector {
  /**
   Ideia geral
   ------------------------------------
   Baixamos o repo local (nesse ponto do programa o repo já existe remotamente).
   
   Para cada projeto git executamos o processamento descrito mais abaixo.
   
   Após o processamento de todos os projetos git, executamos uma operação de push.
   
   Detalhamento do processamento de um projeto git em especifico
   ------------------------------------
   Para cada projeto git, gera-se um arquivo texto com o seguinte nome: 
   <diretório git>_<e-mail>.txt, ex: cerberus-api_flauberjp@gmail.com
   
   Para cada log cujo e-mail seja igual ao do usuário, incluimos o
   id do commit no arquivo. Fazemos isso apenas se o id ainda não existir no 
   arquivo.
   
   Se a alteração tiver sido realizada no arquivo, efetuamos o commit 
   dessa mudança retroativamente, usando a data e hora
   do commit em analise. 
  */
  public static void reflectHistory(List<GitDir> gitDirList, UserGithubInfo userGithubInfo) {
    try {
	  CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(
	      userGithubInfo.getUsername(), userGithubInfo.getPassword());
	  String dir = EvidenceGenerator.getDirOndeRepositorioRemotoSeraClonado(userGithubInfo.getRepoName());
	  Git gitRepo = Git.cloneRepository().setDirectory(new File(dir))
	      .setCredentialsProvider(credentialsProvider).setURI(userGithubInfo.getRepoNameFullPath())
	      .call();

	  StoredConfig config = gitRepo.getRepository().getConfig();
	  config.setString("user", null, "name", userGithubInfo.getGithubName());
	  config.setString("user", null, "email", userGithubInfo.getGithubEmail()); //NOI18N
	  config.save();

	  for(GitDir gitDir: gitDirList) {
		  if(gitDir.getAuthor().equalsIgnoreCase(ProjetosGitDetectadosTableComponent.DESCONSIDERAR_HISTORICO)) {
			  continue;
		  }
		  String fileNameWithItsPath = Util.getSolutionDirectoryIn83Format() + "/" + dir + "/" + new File(gitDir.getPath()).getName() + "_" + gitDir.getAuthor() + ".txt";
		  createFileIfNotExist(fileNameWithItsPath);
	
	      FileRepositoryBuilder builder = new FileRepositoryBuilder();
		  Repository repo = builder.setGitDir(new File(gitDir.getPath() + "\\.git")).setMustExist(true).build();
		
		  Git git = new Git(repo);
		  Iterable<RevCommit> log;
		  log = git.log().call();
		
		  for (Iterator<RevCommit> iterator = log.iterator(); iterator.hasNext();) {
		    RevCommit rev = iterator.next();
		    if(!rev.getCommitterIdent().getEmailAddress().equalsIgnoreCase(gitDir.getAuthor())) {
		      continue;
			}
		    if(Util.isFileContainAString(fileNameWithItsPath, rev.getId().toString())) {
		      continue;
		    }
		    Util.appendStringToAFile(fileNameWithItsPath, rev.getId().toString());
		    
		    AddCommand addCommand = gitRepo.add().addFilepattern(".");
		    addCommand.call();
		    CommitCommand commitCommand = gitRepo.commit();		    
		    commitCommand.setCommitter(new PersonIdent(new PersonIdent(userGithubInfo.getGithubName(), userGithubInfo.getGithubEmail()), rev.getCommitterIdent().getWhen()));
	        commitCommand.setMessage("commit retroativo").call();
		  }
	  }
		  
	  gitRepo.push().setCredentialsProvider(credentialsProvider).call();
	} catch (Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
	}
  }
  
  public static void createFileIfNotExist(String filePath) throws IOException {
    if (!Util.isFileExist(filePath)) {
      File file = new File(filePath);
      file.createNewFile();
    }
  }
}
