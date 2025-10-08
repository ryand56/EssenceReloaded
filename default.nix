{
  lib,
  maven,
  runCommand,

  mvnHash ? "sha256-CYuKrJtLXS1PiL7ABaPJegAn0t++GqVPYiVxA6b7iLY=",
}:
let
  rawJar = maven.buildMavenPackage rec {
    pname = "essence-reloaded";
    version = "1.10.2";

    src = ./.;
    inherit mvnHash;

    mvnParameters = lib.escapeShellArgs [ "-Dmaven.javadoc.skip=true" ];

    installPhase = ''
      runHook preInstall
      cp target/essence-${version}.jar "$out"
      runHook postInstall
    '';

    meta = {
      description = "Stress-free Minecraft server utilities";
      changelog = "https://github.com/ryand56/EssenceReloaded/releases/tag/${version}";
      homepage = "https://github.com/ryand56/EssenceReloaded";
      license = lib.licenses.asl20;
    };
  };
in
runCommand "${rawJar.name}.jar" { } ''
  cp ${rawJar} $out
''

