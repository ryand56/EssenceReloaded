{ mvnHash ? "sha256-4j2yU+X1x+hYI/MTG3d8NRfqoFdIDM3djwvZnhRuG+4=", }:

{
  lib,
  maven,
  runCommand,
}:
let
  rawJar = maven.buildMavenPackage rec {
    pname = "essence-reloaded";
    version = "1.9.0";

    src = ./.;
    inherit mvnHash;

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

