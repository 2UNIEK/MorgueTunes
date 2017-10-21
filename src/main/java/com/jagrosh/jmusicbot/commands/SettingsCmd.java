/*
 * Copyright 2017 John Grosh <john.a.grosh@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jmusicbot.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.Settings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

/**
 *
 * @author John Grosh <john.a.grosh@gmail.com>
 */
public class SettingsCmd extends Command {

    private final Bot bot;
    public SettingsCmd(Bot bot)
    {
        this.bot = bot;
        this.name = "settings";
        this.help = "shows the bots settings";
        this.aliases = new String[]{"status"};
        this.guildOnly = true;
    }
    
    @Override
    protected void execute(CommandEvent event) {
        Settings s = bot.getSettings(event.getGuild());
        MessageBuilder builder = new MessageBuilder()
                .append("\uD83C\uDFA7 **")
                .append(event.getSelfUser().getName())
                .append("** settings:");
        TextChannel tchan = event.getGuild().getTextChannelById(s.getTextId());
        VoiceChannel vchan = event.getGuild().getVoiceChannelById(s.getVoiceId());
        Role role = event.getGuild().getRoleById(s.getRoleId());
        EmbedBuilder ebuilder = new EmbedBuilder()
                .setColor(event.getSelfMember().getColor())
                .setDescription("Text Channel: "+(tchan==null ? "Any" : "**#"+tchan.getName()+"**")
                        + "\nVoice Channel: "+(vchan==null ? "Any" : "**"+vchan.getName()+"**")
                        + "\nDJ Role: "+(role==null ? "None" : "**"+role.getName()+"**")
                        + "\nDefault Playlist: "+(s.getDefaultPlaylist()==null ? "None" : "**"+s.getDefaultPlaylist()+"**")
                        )
                .setFooter(event.getJDA().getGuilds().size()+" servers | "
                        +event.getJDA().getGuilds().stream().filter(g -> g.getSelfMember().getVoiceState().inVoiceChannel()).count()
                        +" audio connections", null);
        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
    }
    
}
