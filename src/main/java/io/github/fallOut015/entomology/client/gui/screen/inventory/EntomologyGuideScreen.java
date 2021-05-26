package io.github.fallOut015.entomology.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.fallOut015.entomology.MainEntomology;
import io.github.fallOut015.entomology.client.gui.widget.button.ChangeVariantButton;
import io.github.fallOut015.entomology.common.capabilities.CapabilitiesEntomology;
import io.github.fallOut015.entomology.entity.EntitiesEntomology;
import io.github.fallOut015.entomology.entity.insect.InsectEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@OnlyIn(Dist.CLIENT)
public class EntomologyGuideScreen extends Screen {
    public static final ResourceLocation ENTOMOLOGY_GUIDE_LOCATION = new ResourceLocation(MainEntomology.MODID, "textures/gui/entomology_guide.png");
    private int currentPage;
    private List<IReorderingProcessor> cachedPageComponents = Collections.emptyList();
    private int cachedPage = -1;
    private ITextComponent header = StringTextComponent.EMPTY;
    private ChangePageButton forwardButton;
    private ChangePageButton backButton;
    private ChangeVariantButton[] variantButtons;
    private final ClientPlayerEntity player;
    private final ClientWorld world;
    private static final TranslationTextComponent HABITAT = new TranslationTextComponent("gui.entomology_guide.habitat");

    private InsectEntity insect;
    private static EntityType<?>[] INSECTS;

    // TODO fix changing screens not letting buttons depress

    public EntomologyGuideScreen(ClientPlayerEntity player) {
        super(NarratorChatListener.NO_TITLE);
        this.player = player;
        this.world = player.clientLevel;
    }

    public void setInsect(@Nonnull EntityType<?> insect) {
        this.insect = (InsectEntity) insect.create(this.world);
        this.insect.setSize(1.0f);
        if(this.insect.hasVariants()) {
            this.insect.setVariant(0);
        }
        this.createButtons();
    }

    public boolean setPage(int page) {
        int i = MathHelper.clamp(page, 0, this.getNumPages() - 1);
        if (i != this.currentPage) {
            this.currentPage = i;
            this.cachedPage = -1;
            this.setInsect(INSECTS[this.currentPage]);
            this.updateButtonVisibility();
            return true;
        } else {
            return false;
        }
    }
    protected boolean forcePage(int page) {
        return this.setPage(page);
    }

    protected void init() {
        if(INSECTS == null) {
            INSECTS = new EntityType<?>[] {
                EntitiesEntomology.BUTTERFLY.get(),
                EntitiesEntomology.FIREFLY.get()
            };
        }

        this.setInsect(EntitiesEntomology.BUTTERFLY.get());
    }

    protected void createButtons() {
        this.buttons.clear();
        int i = (this.width - 26) / 2 - 153;

        this.addButton(new Button(this.width / 2 - 100, 196, 200, 20, DialogTexts.GUI_DONE, (p_214161_1_) -> this.minecraft.setScreen((Screen) null)));

        this.backButton = this.addButton(new ChangePageButton(i + 43, 159, false, button -> this.pageBack(), true));
        this.forwardButton = this.addButton(new ChangePageButton(i + 265, 159, true, button -> this.pageForward(), true));

        if(this.insect.hasVariants()) {
            this.variantButtons = new ChangeVariantButton[this.insect.getVariantsCount()];
            for(int variant = 0; variant < this.insect.getVariantsCount(); ++ variant) {
                this.variantButtons[variant] = this.addButton(new ChangeVariantButton(variant * 21 + 51 + i, 115, variant, this));
            }
            this.variantButtons[0].depress();
        } else {
            this.variantButtons = null;
        }

        this.updateButtonVisibility();
    }

    private int getNumPages() {
        return INSECTS.length;
    }

    protected void pageBack() {
        if (this.currentPage > 0) {
            --this.currentPage;
            this.setInsect(INSECTS[this.currentPage]);
        }

        this.updateButtonVisibility();
    }
    protected void pageForward() {
        if (this.currentPage < this.getNumPages() - 1) {
            ++this.currentPage;
            this.setInsect(INSECTS[this.currentPage]);
        }

        this.updateButtonVisibility();
    }
    public void setVariant(int variant) {
        this.insect.setVariant(variant);

        this.updateButtonVisibility();
    }
    public void unpress() {
        for(ChangeVariantButton button : this.variantButtons) {
            button.unpress();
        }
    }
    private void updateButtonVisibility() {
        this.forwardButton.visible = this.currentPage < this.getNumPages() - 1;
        this.backButton.visible = this.currentPage > 0;
        if(this.variantButtons != null) {
            for(ChangeVariantButton button : this.variantButtons) {
                button.visible = true;
            }
        }
    }

    public boolean keyPressed(int code, int p_231046_2_, int p_231046_3_) {
        if (super.keyPressed(code, p_231046_2_, p_231046_3_)) {
            return true;
        } else {
            switch(code) {
                case 266:
                    this.backButton.onPress();
                    return true;
                case 267:
                    this.forwardButton.onPress();
                    return true;
                default:
                    return false;
            }
        }
    }

    public void render(MatrixStack stack, int x, int y, float partialTicks) {
        AtomicBoolean hasThisInsect = new AtomicBoolean(false);
        AtomicBoolean hasThisVariant = new AtomicBoolean(false);
        this.player.getCapability(CapabilitiesEntomology.INSECT_TRACKER).ifPresent(insectTracker -> {
            if(this.insect.hasVariants()) {
                if(insectTracker.hasVariant((EntityType<? extends InsectEntity>) this.getInsectEntityType(), this.insect.getVariant())) {
                    hasThisVariant.set(true);
                    hasThisInsect.set(true);
                }
            } else {
                if(insectTracker.hasInsect((EntityType<? extends InsectEntity>) this.getInsectEntityType())) {
                    hasThisInsect.set(true);
                }
            }
        });

        this.renderBackground(stack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(ENTOMOLOGY_GUIDE_LOCATION);
        int i = (this.width - 26) / 2 - 153;
        this.blit(stack, i, 2, 0, 0, 168, 192);
        if (this.cachedPage != this.currentPage) {
            if(hasThisInsect.get()) {
                this.header = this.insect.getDisplayName();
            } else {
                StringBuilder mystery = new StringBuilder();
                for(int j = 0; j < this.insect.getDisplayName().getString().length(); ++ j) {
                    if(this.insect.getDisplayName().getString().charAt(j) == ' ') {
                        mystery.append(' ');
                    } else {
                        mystery.append('?');
                    }
                    // spaces
                }
                this.header = new StringTextComponent(mystery.toString());
            }
        }

        int i2 = (this.width - 38) / 2;
        this.minecraft.getTextureManager().bind(ReadBookScreen.BOOK_LOCATION);
        this.blit(stack, i2, 2, 0, 0, 168, 192);

        this.cachedPage = this.currentPage;
        int headerWidth = this.font.width(this.header);
        this.font.draw(stack, this.header, (i - (headerWidth / 2f) + 96f), 12.0F, 0);

        if(this.insect.hasVariants()) {
            ITextComponent subtext;
            if(hasThisVariant.get()) {
                subtext = ((TranslationTextComponent) this.insect.getVariantName()).withStyle(TextFormatting.ITALIC);
            } else {
                StringBuilder mystery = new StringBuilder();
                for(int j = 0; j < this.insect.getVariantName().getString().length(); ++ j) {
                    if(this.insect.getVariantName().getString().charAt(j) == ' ') {
                        mystery.append(' ');
                    } else {
                        mystery.append('?');
                    }
                }
                subtext = new StringTextComponent(mystery.toString()).withStyle(TextFormatting.ITALIC);
            }
            int subtextWidth = this.font.width(subtext);
            this.font.draw(stack, subtext, (i - (subtextWidth / 2f) + 96f), 21.0F, 0);
        }

        Style style = this.getClickedComponentStyleAt((double) x, (double) y);
        if (style != null) {
            this.renderComponentHoverEffect(stack, style, x, y);
        }

        super.render(stack, x, y, partialTicks);

        boolean hasThis = this.insect.hasVariants() ? hasThisVariant.get() : hasThisInsect.get();

        this.minecraft.getTextureManager().bind(ENTOMOLOGY_GUIDE_LOCATION);
        this.blit(stack, i + 115, 99, hasThis ? 87 : 102, 194, 14, 11);

        this.font.draw(stack, HABITAT, (i - (this.font.width(HABITAT) / 2f) + 243f), 12.0F, 0);

        ITextComponent habitat;
        if(this.insect.hasVariants()) {
            habitat = new TranslationTextComponent(((TranslationTextComponent) this.insect.getVariantName()).getKey() + ".habitat");
        } else {
            habitat = new TranslationTextComponent(((TranslationTextComponent) this.insect.getDisplayName()).getKey() + ".habitat");
        }
        List<IReorderingProcessor> list = this.font.split(habitat, 115);
        float ty = 21;
        for(IReorderingProcessor ireorderingprocessor : list) {
            this.font.draw(stack, ireorderingprocessor, i + 185.0F, ty, 0);
            ty += 9;
        }

        if(!hasThis) {
            RenderSystem.color4f(0.0F, 0.0F, 0.0F, 1.0F);
        }

        InventoryScreen.renderEntityInInventory(i + 93, 75, 75, 40.0f, 40.0f, this.insect);
    }

    public boolean mouseClicked(double x, double y, int side) {
        if (side == 0) {
            Style style = this.getClickedComponentStyleAt(x, y);
            if (style != null && this.handleComponentClicked(style)) {
                return true;
            }
        }

        return super.mouseClicked(x, y, side);
    }
    public boolean handleComponentClicked(Style style) {
        ClickEvent clickevent = style.getClickEvent();
        if (clickevent == null) {
            return false;
        } else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            String s = clickevent.getValue();

            try {
                int i = Integer.parseInt(s) - 1;
                return this.forcePage(i);
            } catch (Exception exception) {
                return false;
            }
        } else {
            boolean flag = super.handleComponentClicked(style);
            if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.minecraft.setScreen((Screen)null);
            }

            return flag;
        }
    }
    @Nullable
    public Style getClickedComponentStyleAt(double x, double y) {
        if (this.cachedPageComponents.isEmpty()) {
            return null;
        } else {
            int i = MathHelper.floor(x - (double)((this.width - 192) / 2) - 36.0D);
            int j = MathHelper.floor(y - 2.0D - 30.0D);
            if (i >= 0 && j >= 0) {
                int k = Math.min(128 / 9, this.cachedPageComponents.size());
                if (i <= 114 && j < 9 * k + k) {
                    int l = j / 9;
                    if (l >= 0 && l < this.cachedPageComponents.size()) {
                        IReorderingProcessor ireorderingprocessor = this.cachedPageComponents.get(l);
                        return this.minecraft.font.getSplitter().componentStyleAtWidth(ireorderingprocessor, i);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    public int getUForVariant(int variant) {
        return variant * 17 + 167;
    }
    public int getInsectU() {
        return this.insect.getU();
    }
    public int getInsectV() {
        return this.insect.getV();
    }
    public ClientPlayerEntity getPlayer() {
        return this.player;
    }
    public EntityType<?> getInsectEntityType() {
        return this.insect.getType();
    }
}