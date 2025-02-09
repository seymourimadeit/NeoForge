/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.debug.block;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent.BlockToolModificationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("tool_interact_test")
public class ToolInteractTest {

    private static final Logger LOGGER = LogManager.getLogger();

    public ToolInteractTest() {
        NeoForge.EVENT_BUS.addListener(this::onToolInteraction);
    }

    private void onToolInteraction(final BlockToolModificationEvent event) {
        //Test 1: No Changes, just test if event is called. State and Final State should be the same
        LOGGER.info("BlockState {} is modified to {} at position {} by {} with {} simulated={}", event.getState(), event.getFinalState(), event.getPos(), event.getPlayer(), event.getHeldItemStack(), event.isSimulated());

        //Test 2: Canceling, nothing in game should change
        /*event.setCanceled(true);
        LOGGER.info("Event has been canceled: {}", event.isCanceled());
        */

        //Test 3: Altering
        /*event.setFinalState(Blocks.ACACIA_FENCE.getDefaultState());
        LOGGER.info("BlockState {} is modified to {} at position {} by {} with {}", event.getState(), event.getFinalState(), event.getPos(), event.getPlayer(), event.getHeldItemStack());
        */
    }
}
