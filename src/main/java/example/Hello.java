package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.lib.BaseRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.lib.UserConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple snippet which shows how to open an existing repository
 *
 * @author dominik.stadler at gmx.at
 */
public class Hello implements RequestHandler<Map<String,String>, String> {
    public static void main(String[] args){
        System.out.println(LocalDateTime.now());
        String repoName = "my-git-usage-evidences"; // e.g. "xxxx" or "contributions-cal"
        String github = "https://github.com/jiyose12/"; // e.g. "https://github.com/flauberjp/"
        String username = "jiyose12"; // e.g. flauberjp
        String password = "7532ea969c7fe7c5b82bba8f6341ff08b0e9a7a6"; // e.g. passw0rd
        String githubname = "José Raimundo Fernandes Filho"; // e.g. My github name
        String githubemail = "jiyose@outlook.com"; // e.g. email@domain.com
        if(args.length == 6) {
            repoName = args[0];
            github = args[1];
            username = args[2];
            password = args[3];
            githubname = args[4];
            githubemail = args[5];
        }
        System.out.println(geraEvidenciaDeUsoDoGit(repoName, github, username, password, githubname, githubemail));
        System.out.println(LocalDateTime.now());
    }
    public static boolean geraEvidenciaDeUsoDoGit(String repoName, String github, String username, String password,  String githubname,  String githubemail) {
        String dataEHoraExecucao = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        boolean result;
        try {
            github = github + repoName + ".git";

            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);

            Git git;

            // Get the temporary directory and print it.
            String tempDir = System.getProperty("java.io.tmpdir");

            String dir = tempDir + "/" + repoName;
            Path path = Paths.get(dir);
            if(Files.exists(path)) {
                deleteDirectoryStream(path);
            }
            git = Git.cloneRepository().setDirectory(new File(dir))
                    .setCredentialsProvider(credentialsProvider).setURI(github).call();

            StoredConfig config = git.getRepository().getConfig();
            config.setString("user", null, "name", githubname);
            config.setString("user", null, "email", githubemail); //NOI18N
            config.save();

            // Gera evidencia em index.html
            String fileNameWithItsPath = dir + "/index.html";
            updateEvidenceFile(fileNameWithItsPath, dataEHoraExecucao);

            git.add().addFilepattern(".").call();
            git.commit().setMessage(dataEHoraExecucao).call();
            git.push().setCredentialsProvider(credentialsProvider).call();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    private static void updateEvidenceFile(String fileNameWithItsPath, String dataEHoraExecucao) {
        Path filePath = Paths.get(fileNameWithItsPath);
        try {
            Stream<String> lines = Files.lines(filePath, Charset.forName("UTF-8"));
            List<String> replacedLine = lines
                    .map(line ->
                        line.replace("<tbody>", "<tbody><tr><td>" + dataEHoraExecucao + "</td></tr>")
                    )
                    .collect(Collectors.toList());
            Files.write(filePath, replacedLine, Charset.forName("UTF-8"));
            lines.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private static void deleteDirectoryStream(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    // Handler value: example.Handler
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public String handleRequest(Map<String,String> event, Context context)
    {
        String repoName = System.getenv("repoName");
        String github = System.getenv("github");
        String username = System.getenv("username");
        String password = System.getenv("password");
        String githubname = System.getenv("githubname");
        String githubemail = System.getenv("githubemail");

        LambdaLogger logger = context.getLogger();
        String response = geraEvidenciaDeUsoDoGit(repoName, github, username, password, githubname, githubemail) ? "200 OK" : "500 Bad Request";
        // log execution details
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("CONTEXT: " + gson.toJson(context));
        // process event
        logger.log("EVENT: " + gson.toJson(event));
        logger.log("EVENT TYPE: " + event.getClass().toString());
        return response;
    }
}
