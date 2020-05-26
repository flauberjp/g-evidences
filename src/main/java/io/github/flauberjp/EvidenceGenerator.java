package io.github.flauberjp;

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

public class EvidenceGenerator {

    public static void main(String[] args){
        System.out.println("Programa iniciado às: " + LocalDateTime.now());

        UserGithubInfo userGithubInfo = new UserGithubInfo(args);
        System.out.println(geraEvidenciaDeUsoDoGit(userGithubInfo));

        System.out.println("Programa finalizado às: " + LocalDateTime.now());
    }

    public static boolean geraEvidenciaDeUsoDoGit(UserGithubInfo userGithubInfo) {
        String dataEHoraExecucao = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        boolean result;
        try {

            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(
                    userGithubInfo.getUsername(), userGithubInfo.getPassword());

            Git git;

            String dir = getDirOndeRepositorioRemotoSeraClonado(userGithubInfo.getRepoName());

            git = Git.cloneRepository().setDirectory(new File(dir))
                    .setCredentialsProvider(credentialsProvider).setURI(userGithubInfo.getRepoNameFullPath()).call();

            StoredConfig config = git.getRepository().getConfig();
            config.setString("user", null, "name", userGithubInfo.getGithubName());
            config.setString("user", null, "email", userGithubInfo.getGithubEmail()); //NOI18N
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

    private static String getDirOndeRepositorioRemotoSeraClonado(String repoName) throws IOException {
        String dir = System.getProperty("java.io.tmpdir");
        dir += "/" + repoName;
        Path path = Paths.get(dir);
        deletaDirDoRepositorioCasoExista(path);
        return dir;
    }

    private static void deletaDirDoRepositorioCasoExista(Path path) throws IOException {
        if(Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
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
}
