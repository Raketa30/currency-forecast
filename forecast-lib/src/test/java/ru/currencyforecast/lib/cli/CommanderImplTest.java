package ru.currencyforecast.lib.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.currencyforecast.lib.controller.Controller;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommanderImplTest {

    @Mock
    private Controller controller;

    @InjectMocks
    private CommanderImpl commander;


    @Test
    void shouldReturnTrueWhenCorrectHelpCommand() {
        assertThat(commander.execute("-help")).isTrue();
    }

    @Test
    void shouldReturnFalseWhenHelpCommandWithOtherCommandsTest() {
        assertThat(commander.execute("-help rate USD -output")).isFalse();
        assertThat(commander.execute("-help differentWord")).isFalse();
    }

    @Test
    void shouldReturnTrueWithSingleCurrencyRatePeriodCommandOutputListOrGraphTest() {
        assertThat(commander.execute("rate USD -period month -alg mistic -output list")).isTrue();
        assertThat(commander.execute("rate USD -period week -alg mistic -output graph")).isTrue();
    }

    @Test
    void shouldReturnFalseWithSingleCurrencyRateDateCommandAndOutputListOrGraphTest() {
        assertThat(commander.execute("rate USD -date 23.05.2021 -alg mistic -output list")).isFalse();
        assertThat(commander.execute("rate USD -date tomorrow -alg mistic -output graph")).isFalse();
    }

    @Test
    void shouldReturnFalseWithMultiCurrencyRatePeriodCommandAndOutputListTest() {
        assertThat(commander.execute("rate USD,EUR -period month -alg mistic -output list")).isFalse();
        assertThat(commander.execute("rate USD,AMD -period week -alg mistic -output list")).isFalse();
    }

    @Test
    void shouldReturnTrueWithMultiCurrencyRatePeriodCommandAndOutputListTest() {
        assertThat(commander.execute("rate USD,EUR -period month -alg mistic -output graph")).isTrue();
        assertThat(commander.execute("rate USD,AMD -period week -alg mistic -output graph")).isTrue();
    }

    @Test
    void shouldReturnFalseWithWrongCommandsAndWrongArgumentsTest() {
        assertThat(commander.execute("rate USD,EUR -bla month -alg mistic -output")).isFalse();
        assertThat(commander.execute("rate -period week -alg mistic -output list")).isFalse();
        assertThat(commander.execute("wrong args")).isFalse();
    }

}