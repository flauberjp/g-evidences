package example;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple snippet which shows how to open an existing repository
 *
 * @author dominik.stadler at gmx.at
 */
public class Hello {
    public static void main(String[] args){
        System.out.println(LocalDateTime.now());
        String repoName = "<a_repo_name>"; // e.g. "xxxx" or "my-git-usage-evidences"
        String github = "<a_git_hub_account_link>"; // e.g. "https://github.com/flauberjp/"
        String username = "<git_hub_user>"; // e.g. flauberjp
        String password = "<git_hub_password>"; // e.g. passw0rd
        String githubname = "<my_github_name>"; // e.g. My github name
        String githubemail = "<my_github_email>"; // e.g. email@domain.com
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
}
