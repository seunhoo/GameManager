package org.gameManager.Enum;

import org.bukkit.ChatColor;

import java.util.*;

public enum GameManagerMessage {
    DEFAULT_MESSAGE(ChatColor.GOLD + "" + ChatColor.BOLD + "[GameManager] " + ChatColor.RESET),
    // Game Message
    GAME_CREATE_MESSAGE(ChatColor.AQUA + "게임이 생성 되었습니다."),
    GAME_START_MESSAGE(ChatColor.AQUA + "게임이 시작 되었습니다."),
    GAME_STOP_MESSAGE(ChatColor.AQUA + "게임이 종료 되었습니다."),
    GAME_DELETE_MESSAGE(ChatColor.AQUA + "게임이 삭제 되었습니다."),
    GAME_SET_SPAWN_MESSAGE(ChatColor.AQUA + "게임 스폰장소가 지정 되었습니다."),

    // info message
    INFO_MESSAGE_GAME_LIST(ChatColor.LIGHT_PURPLE + " :: 게임 목록 :: "),
    INFO_MESSAGE_GAME_NOW(ChatColor.LIGHT_PURPLE + " :: 현재 게임 :: "),

    // info command
    INFO_COMMAND_EXPLAIN1(ChatColor.LIGHT_PURPLE + " :: 게임시작 플러그인 ::"),
    INFO_COMMAND_EXPLAIN2(ChatColor.AQUA +  "/게임생성" + ChatColor.WHITE + " 이름"),
    INFO_COMMAND_EXPLAIN3(ChatColor.YELLOW + "└ 게임 **이 생성됩니다."),
    INFO_COMMAND_EXPLAIN4(ChatColor.AQUA +  "/게임삭제"+ ChatColor.WHITE +" 이름"),
    INFO_COMMAND_EXPLAIN5(ChatColor.YELLOW +"└ 생성했던 게임을 삭제합니다."),
    INFO_COMMAND_EXPLAIN6(ChatColor.AQUA +  "/게임시작"+ ChatColor.WHITE +" 이름"),
    INFO_COMMAND_EXPLAIN7(ChatColor.YELLOW +"└ 생성한 게임을 시작합니다."),
    INFO_COMMAND_EXPLAIN8(ChatColor.AQUA +  "/게임종료"),
    INFO_COMMAND_EXPLAIN9(ChatColor.YELLOW + "└ 진행중인 모든게임이 종료됩니다."),
    INFO_COMMAND_EXPLAIN10(ChatColor.AQUA +  "/게임스폰설정"+ ChatColor.WHITE +" 이름 " + "<플레이어이름>"),
    INFO_COMMAND_EXPLAIN11(ChatColor.YELLOW +"└ 생성했던 게임이 시작될시 어디서 스폰될지 설정해줍니다."),
    INFO_COMMAND_EXPLAIN12(ChatColor.YELLOW + "└ !! 플레이어가 죽을수도 있어서 게임이 시작되는동안에 죽을시 스폰지정한곳에서 부활 !!"),
    INFO_COMMAND_EXPLAIN13(ChatColor.YELLOW + "└ !! <플레이어이름> 을 지정하여 게임별 플레이어 개인 스폰위치 조절가능 !!"),
    INFO_COMMAND_EXPLAIN14(ChatColor.AQUA + "/게임목록"),
    INFO_COMMAND_EXPLAIN15(ChatColor.YELLOW +"└ 현재 설정된 게임 목록을 보여줍니다."),
    INFO_COMMAND_EXPLAIN16(ChatColor.YELLOW +"/게임확인"),
    INFO_COMMAND_EXPLAIN17(ChatColor.YELLOW +"└ 현재 게임중인 게임을 확인합니다."),

    COMMAND_CREATE("게임생성"),
    COMMAND_DELETE("게임삭제"),
    COMMAND_START("게임시작"),
    COMMAND_STOP("게임종료"),
    COMMAND_SET_SPAWN("게임스폰설정"),
    COMMAND_HELP("게임도움말"),
    COMMAND_GAME_LIST("게임목록"),
    COMMAND_NOW("게임확인"),

    ERROR("오류"),
    ERROR_WRONG_COMMAND(ChatColor.RED + "잘못된 명령입니다."),
    ERROR_GAME_IS_NOT_START(ChatColor.RED + "진행중인 게임이 없습니다."),
    ERROR_GAME_IS_ALREADY_START(ChatColor.RED + "게임이 이미 진행중입니다."),
    ERROR_GAME_IS_NOT_EXITS(ChatColor.RED + "게임이 존재하지 않습니다."),
    ERROR_GAME_IS_NOT_EXITS_SPAWN(ChatColor.RED + "게임의 스폰위치가 존재하지 않습니다."),

    DEFAULT_SPAWN("기본"),
    ;

    private final String message;

    GameManagerMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    private static final Map<String, GameManagerMessage> messageInfoMap = new HashMap<>();

    static {
        for (GameManagerMessage gameManagerMessage : EnumSet.range(COMMAND_CREATE, COMMAND_NOW)) {
            messageInfoMap.put(gameManagerMessage.message, gameManagerMessage);
        }
    }

    public static GameManagerMessage getByMessage(String message) {
        return messageInfoMap.getOrDefault(message,ERROR);
    }
}
