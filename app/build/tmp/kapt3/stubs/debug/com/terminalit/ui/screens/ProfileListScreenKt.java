package com.terminalit.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001aH\u0010\b\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u00a8\u0006\u000f"}, d2 = {"ProfileCard", "", "profile", "Lcom/terminalit/model/ServerProfile;", "onConnect", "Lkotlin/Function0;", "onEdit", "onDelete", "ProfileListScreen", "onNavigateToCreate", "onNavigateToEdit", "Lkotlin/Function1;", "", "viewModel", "Lcom/terminalit/viewmodel/ProfileListViewModel;", "app_debug"})
public final class ProfileListScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ProfileListScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCreate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.terminalit.model.ServerProfile, kotlin.Unit> onConnect, @org.jetbrains.annotations.NotNull()
    com.terminalit.viewmodel.ProfileListViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProfileCard(@org.jetbrains.annotations.NotNull()
    com.terminalit.model.ServerProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConnect, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
}