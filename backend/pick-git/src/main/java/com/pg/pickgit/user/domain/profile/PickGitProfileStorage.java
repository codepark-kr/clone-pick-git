package com.pg.pickgit.user.domain.profile;

import java.io.File;
import java.util.Optional;

public interface PickGitProfileStorage {

    Optional<String> store(File file, String username);

    String storeByteFile(byte[] file, String username);
}
