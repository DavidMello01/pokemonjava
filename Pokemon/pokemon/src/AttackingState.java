class AttackingState implements State {
    @Override
    public void animate(AnimationPanel panel) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 20; i++) {
                        panel.setAttackerX(panel.getAttackerX() + 10);
                        panel.repaint();
                        Thread.sleep(50);
                    }
                    for (int i = 0; i < 20; i++) {
                        panel.setAttackerX(panel.getAttackerX() - 10);
                        panel.repaint();
                        Thread.sleep(50);
                    }
                    panel.setState(new ReceivingStatew()); // Troca para o estado de recebendo ataque após animar o ataque
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
