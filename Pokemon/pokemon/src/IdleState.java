class IdleState implements State {
    @Override
    public void animate(AnimationPanel panel) {
        // O estado ocioso não faz animação
        panel.repaint();
    }
}
