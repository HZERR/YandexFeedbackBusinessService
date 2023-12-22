package ru.hzerr.configuration.database;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import ru.hzerr.configuration.ExtendedStructureConfiguration;
import ru.hzerr.configuration.database.exception.FileSystemDatabaseAddProcessingException;
import ru.hzerr.configuration.database.exception.FileSystemDatabaseGetProcessingException;
import ru.hzerr.fx.engine.core.LoadException;
import ru.hzerr.generator.GenerationException;
import ru.hzerr.generator.ILoginGenerator;
import ru.hzerr.generator.RandomAlphanumericLoginGenerator;
import ru.hzerr.model.CreationStatus;
import ru.hzerr.model.Gender;
import ru.hzerr.model.MailRuAccount;

import java.time.LocalDateTime;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileSystemMailRuDatabaseTest {

    private FileSystemMailRuDatabase database;
    private MailRuAccount account1;
    private MailRuAccount newAccount;
    private ILoginGenerator loginGenerator = new RandomAlphanumericLoginGenerator(12);

    @BeforeEach
    void setUp() throws LoadException, GenerationException {
        database = new FileSystemMailRuDatabase(new MailRuDatabaseFileLoader(new ExtendedStructureConfiguration()).load());

        account1 = new MailRuAccount();
        account1.setCreated(false);
        account1.setLogin("test23232@mail.ru");
        account1.setDateOfBirth("01.01.2000");
        account1.setReservedEmail("test2@mail.ru");
        account1.setFirstName("Firstname");
        account1.setLastName("Lastname");
        account1.setGender(Gender.MALE);
        account1.setPassword("test");
        account1.setCreationStatus(CreationStatus.DEFAULT);
        account1.setCreatedDate(LocalDateTime.now());
        account1.setBlocked(false);

        newAccount = new MailRuAccount();
        newAccount.setLogin(loginGenerator.generate());
    }

    @Test
    @Order(1)
    void add() {
        database.add(newAccount.getLogin(), newAccount);
        database.add(account1.getLogin(), account1);
        Assertions.assertEquals(database.get(newAccount.getLogin(), MailRuAccount.class), newAccount);
        Assertions.assertThrows(FileSystemDatabaseAddProcessingException.class, () -> database.add(account1.getLogin(), account1));
    }

    @Test
    @Order(2)
    void get() {
        database.add(newAccount.getLogin(), newAccount);
        Assertions.assertEquals(database.get(newAccount.getLogin(), MailRuAccount.class), newAccount);
    }

    @Test
    @Order(3)
    void update() {
        database.update(account1.getLogin(), newAccount);
        Assertions.assertEquals(database.get(account1.getLogin(), MailRuAccount.class), newAccount);
    }

    @Test
    @Order(4)
    void remove() {
        database.remove(account1.getLogin());
        Assertions.assertThrows(FileSystemDatabaseGetProcessingException.class, () -> database.get(account1.getLogin(), MailRuAccount.class));
    }
}