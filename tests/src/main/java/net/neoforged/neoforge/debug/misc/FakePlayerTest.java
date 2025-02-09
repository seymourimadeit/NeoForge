/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.debug.misc;

import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod("fake_player_test")
public class FakePlayerTest {
    public static final boolean ENABLE = true;

    public FakePlayerTest() {
        if (ENABLE)
            NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("fakeplayer")
                //Tests fake player creation & interaction with other entities in world
                .then(Commands.literal("attack")
                        .then(Commands.argument("target", EntityArgument.entity())
                                .executes(context -> {
                                    FakePlayerFactory.getMinecraft(context.getSource().getLevel()).attack(EntityArgument.getEntity(context, "target"));
                                    return 1;
                                })))
                //Makes sure fake player does not crash when its `connection` is accessed [#7604]
                .then(Commands.literal("opencontainer")
                        .executes(context -> {
                            ServerPlayer fakePlayer = FakePlayerFactory.getMinecraft(context.getSource().getLevel());
                            InteractionHand hand = InteractionHand.MAIN_HAND;
                            ItemStack stack = Items.WRITABLE_BOOK.getDefaultInstance();
                            fakePlayer.setItemInHand(hand, stack);
                            fakePlayer.openItemGui(stack, hand);
                            return 1;
                        })));
    }
}
